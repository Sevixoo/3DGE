precision mediump float;

varying highp vec2 textureCoordinate;
uniform sampler2D vScreenTexture;

void main() {
    gl_FragColor = vec4(vec3(1.0 - texture2D(vScreenTexture, textureCoordinate)), 1.0);
}