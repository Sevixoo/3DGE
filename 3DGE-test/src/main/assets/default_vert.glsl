
layout ( location = 0 ) in vec4 vPosition;

out vec3 colour;

void main(){
    gl_Position = vPosition;
    colour = vec3(vPosition.x+0.5,0.0,vPosition.y+0.5);
}