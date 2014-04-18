#version 330

uniform mat4 g_WorldViewProjectionMatrix;

attribute vec3 inPosition;
attribute vec2 inTexCoord;

varying float v_length;


void main() { 
    // Vertex transformation 
    v_length = inTexCoord.x;
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}