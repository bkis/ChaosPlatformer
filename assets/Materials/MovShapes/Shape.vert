#version 330

attribute vec3 inPosition;

uniform mat4 g_WorldViewProjectionMatrix;
uniform vec3 m_Position;
uniform float m_Scale;

void main() { 
    // Vertex transformation 
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition * m_Scale, 1.0);
}
