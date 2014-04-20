#version 330

uniform mat4 g_WorldViewProjectionMatrix;
attribute vec3 inPosition;
attribute vec2 inTexCoord;

varying vec2 v_coord;

void main() { 
    // Vertex transformation 
    v_coord = inTexCoord;
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
}
