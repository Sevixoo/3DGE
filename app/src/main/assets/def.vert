uniform mat4 uMVPMatrix;

attribute vec4 vNormal;
attribute vec4 vPosition;

uniform vec3 cameraPosition;

void main() {
    gl_Position = uMVPMatrix * vPosition;
}