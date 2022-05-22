uniform float g_Time;
uniform float m_SpeedFactor;
uniform float m_Trail;
varying vec2 texCoord;

#define PI2 (2.0 * 3.141592)

void main() {
    vec2 p = (texCoord - vec2(0.5, 0.5)) * 2.0;
    float dist = length(p);
    float val = 0.0;

    // outer circle
    val += smoothstep(0.75, 0.85, dist);
    val -= smoothstep(0.9, 1.0, dist);

    // clock pointer
    if (dist < 0.9) {
        float angle = atan(p.y, p.x) / PI2;
        float dAngle = fract(g_Time * m_SpeedFactor + angle);

        if (dAngle < 0.5) {
            val += 1.0 - smoothstep(0.0, m_Trail, dAngle * dist);
        }
    }
    
    gl_FragColor = vec4(1.0, 1.0, 1.0, clamp(val, 0.0, 1.0))
;
}

