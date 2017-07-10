attribute vec4 vPosition;
attribute vec4 vTextureCoordinate;

varying vec2 textureCoordinate;

void main() {
    gl_Position = vPosition;
    textureCoordinate = vTextureCoordinate.xy;
}