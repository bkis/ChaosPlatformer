#version 330

uniform vec3 m_Color;
uniform float m_SegmentCount;
uniform float m_TotalLength;
uniform float m_CurrentSegment;

varying float v_length;

void main() {
    float relLength = 1.0 - (m_CurrentSegment - v_length) / m_SegmentCount;

    // Set the fragment color for example to gray, alpha 1.0
    vec4 color;

    if (relLength < 0.1 || relLength > 1.0)
        discard;

    if (relLength < 0.5)
        color = mix(vec4(0, 0, 0, 0), vec4(m_Color, 1), relLength*2.0);
    else if (relLength < 1.0)
        color = mix(vec4(m_Color, 1), vec4(1, 1, 1, 1), relLength*2.0 - 1.0);

    gl_FragColor = color;
}
