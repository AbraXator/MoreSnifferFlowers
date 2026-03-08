#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

//HSV (hue, saturation, value) to RGB.
//Sources: https://gist.github.com/yiwenl/745bfea7f04c456e0101, https://gist.github.com/sugi-cho/6a01cae436acddd72bdf
vec3 hsv2rgb(vec3 c){
	vec4 K=vec4(1.,2./3.,1./3.,3.);
	return c.z*mix(K.xxx,clamp(abs(fract(c.x+K.xyz)*6.-K.w)-K.x, 0.,1.),c.y);
}

//RGB to HSV.
//Source: https://gist.github.com/yiwenl/745bfea7f04c456e0101
vec3 rgb2hsv(vec3 c) {
	float cMax=max(max(c.r,c.g),c.b),
	      cMin=min(min(c.r,c.g),c.b),
	      delta=cMax-cMin;
	vec3 hsv=vec3(0.,0.,cMax);
	if(cMax>cMin){
		hsv.y=delta/cMax;
		if(c.r==cMax){
			hsv.x=(c.g-c.b)/delta;
		}else if(c.g==cMax){
			hsv.x=2.+(c.b-c.r)/delta;
		}else{
			hsv.x=4.+(c.r-c.g)/delta;
		}
		hsv.x=fract(hsv.x/6.);
	}
	return hsv;
}

void main() {
    vec4 sprite = texture(Sampler0, texCoord0);
    if (sprite.a < 0.1) {
        discard;
    }

    vec3 goldColor = vec3(230. / 255., 190. / 255., 33. / 255.);

    vec3 hsvSprite = rgb2hsv(sprite.rgb);
    hsvSprite.g *= 0.20; // grayscale
    vec3 rgbSprite = hsv2rgb(hsvSprite);

    vec3 recolored = rgbSprite * goldColor;

    float highlightLim = 0.75;
    if(hsvSprite.b > highlightLim){
        float highlight = (hsvSprite.b - highlightLim) * (1. / highlightLim);

        recolored = mix(recolored, vec3(1., 0.9, 0.7), highlight * 3.);
    }


    float shadowLim = 0.40;
    if(hsvSprite.b < shadowLim){
        float shadow = (shadowLim - hsvSprite.b) * (1. / shadowLim);

        recolored = mix(recolored, vec3(0.3, 0.1, 0.0), shadow * 2.);
    }

    fragColor = linear_fog(vec4(recolored, sprite.a) * ColorModulator * vertexColor, vertexDistance, FogStart, FogEnd, FogColor);
}
