varying vec3 vertex;
varying vec3 originVertex;
varying vec3 normal;

uniform vec3 modelPosition;
uniform mat4 modelMatrix;
uniform mat4 viewProjectionMatrix;

void main(void) {

    vertex = vec3(gl_Vertex) + modelPosition;
    originVertex = vec3(gl_Vertex);
    normal = vec3(gl_Normal);

    gl_Position = viewProjectionMatrix * modelMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;

}