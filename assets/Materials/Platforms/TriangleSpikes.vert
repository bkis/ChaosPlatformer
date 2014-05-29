#version 330

attribute vec3 inPosition;
attribute vec4 inColor;

uniform mat4 g_WorldViewProjectionMatrix;
uniform vec3 m_Position;

varying vec4 color;

void main() { 
    // Vertex transformation 
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
    color = inColor;
}
