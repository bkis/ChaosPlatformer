uniform mat4 g_WorldViewProjectionMatrix;

uniform vec3 m_Offset;

attribute vec3 inPosition;
attribute vec2 inTexCoord;

varying float v_length;


void main() { 
    // Vertex transformation 
    v_length = inTexCoord.x;
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition + m_Offset, 1.0);
}
