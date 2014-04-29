package pt.edj.cp.world;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.terrain.noise.basis.ImprovedNoise;
import java.util.Collection;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import pt.edj.cp.app.IngameState;
import pt.edj.cp.input.IMovementListener;
import pt.edj.cp.world.platforms.Platform;


public class PlatformLifecycleManager implements IMovementListener {
    
    private Random random = new Random();
        
    private float zoneSize;
    private Vector2f activeAreaSize;
    private Vector2f halfArea;
    
    private float platformSpawnThreshold = 0.5f;
    private IPlatformSpawner platformSpawner;
    
    /**
     * Used for zone management
     */
    private static class Position implements Comparable<Position> {
        public int x;
        public int y;
        
        public Position(int xx, int yy) {
            x = xx;
            y = yy;
        }

        public int compareTo(Position o) {
            if (x != o.x)
                return x - o.x;
            return y - o.y;
        }
    }
    
    
    /**
     * Used for zonemanagement
     */
    private static class IntRect implements Comparable<IntRect> {
        public Position p1;
        public Position p2;
        
        private void addCol(Collection<Position> into, int x) {
            for (int y = p1.y; y <= p2.y; y++)
                into.add(new Position(x, y));
        }
        
        private void addRow(Collection<Position> into, int y) {
            for (int x = p1.x; x <= p2.x; x++)
                into.add(new Position(x, y));
        }
        
        public int compareTo(IntRect o) {
            int d1 = p1.compareTo(o.p1);
            if (d1 != 0) return d1;
            return p2.compareTo(o.p2);
        }
        
        // Returns all positions within this rect that are not included in the
        // other one
        public Iterable<Position> diff(IntRect other) {
            TreeSet<Position> result = new TreeSet<Position>();
            for (int x = p1.x; x < other.p1.x; x++) addCol(result, x);
            for (int x = other.p2.x + 1; x <= p2.x; x++) addCol(result, x);
            for (int y = p1.y; y < other.p1.y; y++) addRow(result, y);
            for (int y = other.p2.y + 1; y <= p2.y; y++) addRow(result, y);
            return result;
        }
        
        // returns all positions in this rectangle
        public Iterable<Position> allPositions() {
            TreeSet<Position> result = new TreeSet<Position>();
            for (int x = p1.x; x <= p2.x; x++) addCol(result, x);
            return result;
        }
    }
    
    
    /**
     * Basic spatial units to decide on platform creation, placement and
     * destruction
     */
    private class Zone extends Position {
        public Platform platform;
        
        public Zone(Position p) {
            super(p.x, p.y);
            
            if (platformSpawner.shouldPlacePlatform(p)) {
                platform = ingameState.addPlatform(generatePlatformPosition(p));
            } else {
                platform = null;
            }
        }
        
        public void delete() {
            if (platform != null)
                ingameState.removePlatform(platform);
        }
    }

    
    private IntRect getActiveZonesForPosition(Vector2f playerPos) {
        Vector2f p1 = playerPos.subtract(halfArea).divide(zoneSize);
        Vector2f p2 = playerPos.add(halfArea).divide(zoneSize);
        
        IntRect result = new IntRect();
        result.p1 = new Position((int) Math.floor(p1.x), (int) Math.floor(p1.y));
        result.p2 = new Position((int) Math.ceil(p2.x), (int) Math.ceil(p2.y));
        return result;
    } 
    
    
    private TreeMap<Position,Zone> zones = new TreeMap<Position,Zone>();
    private IntRect activeZones;
    
    IngameState ingameState; 
    
    
    public PlatformLifecycleManager(IngameState ingame, float zoneSize, Vector2f activeArea) {
        this.zoneSize = zoneSize;
        this.activeAreaSize = activeArea.clone();
        this.halfArea = activeArea.mult(0.5f);
        
        this.ingameState = ingame;
        
//        this.platformSpawner = new NoisePlatformSpawner(0.8525342f);
//        this.platformSpawner = new HorizontalPlatformSpawner();
        this.platformSpawner = new RandomPlatformSpawner();
        this.platformSpawnThreshold = 0.8f;
        
        // add initial zones around player
        activeZones = getActiveZonesForPosition(new Vector2f(0.0f, 0.0f));
        for (Position pos : activeZones.allPositions()) {
            zones.put(pos, new Zone(pos));
        }
    }
    
    
    public void movement(Vector3f newPosition, Vector3f delta) {
        Vector2f playerPos = new Vector2f(newPosition.x, newPosition.y);
        IntRect newActiveZones = getActiveZonesForPosition(playerPos);
        
        if (activeZones.compareTo(newActiveZones) != 0) {
            /*
            System.out.printf("Move to: (%3.2f %3.2f) new area: (%d:%d to %d:%d)", 
                    playerPos.x, playerPos.y, 
                    newActiveZones.p1.x, newActiveZones.p1.y,
                    newActiveZones.p2.x, newActiveZones.p2.y);
            */

            // see if we have to delete old zones
            for (Position pos : activeZones.diff(newActiveZones)) {
                zones.remove(pos).delete();
            }

            // add new zones?
            for (Position pos : newActiveZones.diff(activeZones)) {
                zones.put(pos, new Zone(pos));
            }

            activeZones = newActiveZones;
        }
    }
    
    
    /**
     * Decide whether or not to place a new platform at the given spot
     */
    private interface IPlatformSpawner {
        public boolean shouldPlacePlatform(Position zonePosition);
    }
    
    /**
     * Use random choice to make choice for each zone
     */
    private class RandomPlatformSpawner implements IPlatformSpawner {
        public boolean shouldPlacePlatform(Position zonePosition) {
            return random.nextFloat() < platformSpawnThreshold;
        }
    }
    
    /**
     * Use noise to create more cluster-like platform density
     */
    private class NoisePlatformSpawner implements IPlatformSpawner {
        float stretch;
        
        public NoisePlatformSpawner(float stretch) {
            this.stretch = stretch;
        }
        
        public boolean shouldPlacePlatform(Position zonePosition) {
            float v = ImprovedNoise.noise(zonePosition.x / stretch, zonePosition.y / stretch, 0.0f);
            return (v + 0.5f) < platformSpawnThreshold;
        }
    }
    
    /**
     * Generate horizontal platform lines
     */
    private class HorizontalPlatformSpawner implements IPlatformSpawner {
        public boolean shouldPlacePlatform(Position zonePosition) {
            // get y-dependent spacing
            Random yRand = new Random(zonePosition.y);
            int period = 30 + yRand.nextInt(10);
            int offset = yRand.nextInt(period);
            
            // get x-dependent length
            int number = (zonePosition.x - offset) / period;
            Random xRand = new Random(zonePosition.y + number);
            int length = 10 + xRand.nextInt(6);
            
            int within = (zonePosition.x - offset) % period;
            within = (within + period) % period;
            return within < length;
        }
    }
    
    
    Vector3f generatePlatformPosition(Position zonePosition) {
        float maxDiff = 0.1f;
        float x = (zonePosition.x + 0.5f - maxDiff + maxDiff * 2 * random.nextFloat()) * zoneSize;
        float y = (zonePosition.y + 0.5f - maxDiff + maxDiff * 2 * random.nextFloat()) * zoneSize;
        return new Vector3f(x, y, 0.0f);
    }
}
