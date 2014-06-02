uniform float g_Time;

uniform vec4 m_Color;
uniform float m_RelTime;
uniform float m_StartRadius;
uniform float m_MidRadius1;
uniform float m_MidRadius2;
uniform float m_EndRadius;

varying vec2 texCoord;


void main() {
    vec2 p = (texCoord - vec2(0.5, 0.5)) * 2.0;
    float dist = length(p);

    if (dist < m_StartRadius || dist > m_EndRadius)
        discard;

    float strength = smoothstep(m_StartRadius, m_MidRadius1, dist)
                    - smoothstep(m_MidRadius2, m_EndRadius, dist);

    float alpha = pow(1-m_RelTime, 2.f);
    
    gl_FragColor = vec4(m_Color.rgb, alpha * strength);
}

