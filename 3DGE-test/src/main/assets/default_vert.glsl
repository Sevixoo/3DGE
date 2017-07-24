#version 300 es\n

in vec3 vPosition;

out vec3 colour;

void main(){
    gl_Position = vec4(vPosition,1.0f);
    colour = vec3(vPosition.x+0.5,0.0,vPosition.y+0.5);
}