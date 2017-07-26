
layout ( location = 0 ) in vec4 vPosition;

uniform mat4 uMVPmatrix;
uniform float uPointSize;

void main(){
    gl_Position = uMVPmatrix * vPosition;
    gl_PointSize = uPointSize;
}