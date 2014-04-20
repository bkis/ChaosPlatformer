uniform sampler2D m_PreRenderedScene;

varying vec2 v_coord;

void main() {
    // Set the fragment color for example to gray, alpha 1.0
    vec4 color = texture2D(m_PreRenderedScene, vec2(v_coord.s, 1.0-v_coord.t));
    gl_FragColor = vec4(color.rgb, 1.0);
}

