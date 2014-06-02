uniform vec3 m_Color;
uniform vec2 m_Extents;
uniform float m_Highlight;

varying float v_Distance;

float interp(float x, float th, float minVal, float maxVal) {
    if (x < th)
        return mix(minVal, maxVal, smoothstep(0.0, th, x));
    else 
        return mix(maxVal, 0.0, smoothstep(th, 1.0, x));
}

void main() {
    float exponent = 3.0f - 1.5 * m_Highlight;

    vec3 color = mix(m_Color, vec3(1,1,1), pow(v_Distance, exponent));
    
    float minAlpha = mix(0.05, 0.2, m_Highlight);
    float maxAlpha = mix(0.15, 0.6, m_Highlight);
    float alpha = interp(v_Distance, 0.7, minAlpha, maxAlpha);

    //alpha = 1;
    gl_FragColor = vec4(color, alpha);
}

