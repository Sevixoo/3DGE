uniform mat4 uMVPMatrix;

attribute vec4 vPosition;
attribute vec4 vNormal;

void main() {
    gl_Position = uMVPMatrix * vPosition;
}