#version 300 es\n

layout(location = 0) in vec4 a_position;
//layout(location = 1) in vec4 a_color;

out vec4 v_color;

void main(){
    v_color = vec4(1.0,0,0,1.0);
    gl_Position = a_position;
}