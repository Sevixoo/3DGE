precision mediump float;

uniform vec4 uColor;

in vec3 colour;
out vec4 outColor;

void main(){
    outColor = vec4( colour.xyz, 1.0 ) ;
}