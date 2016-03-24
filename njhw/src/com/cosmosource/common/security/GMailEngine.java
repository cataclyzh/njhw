
package com.cosmosource.common.security;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * JCaptcha验证码图片生成引擎,仿照JCaptcha2.0编写类似GMail验证码的样式.
 * 
 * @author WXJ
 */
public class GMailEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4; //验证码最小字符数
		int maxWordLength = 5; //验证码最大字符数
		int fontSize = 24; //验证码字符大小
		int imageWidth = 100; //验证码图片宽度
		int imageHeight = 40; //验证码图片高度

		//word generator
		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		
		//word2image components
		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength,
				new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),
						new Color(23, 67, 172) }), new TextDecorator[] {});
		
		//定义验证码图片背景
		SingleColorGenerator leftUpColor = new SingleColorGenerator(Color.WHITE);
		SingleColorGenerator leftDownColor = new SingleColorGenerator(Color.LIGHT_GRAY);
		SingleColorGenerator rightUpColor = new SingleColorGenerator(Color.LIGHT_GRAY);
		SingleColorGenerator rightDownColor = new SingleColorGenerator(Color.WHITE);
		BackgroundGenerator background = new FunkyBackgroundGenerator(imageWidth, imageHeight, 
				leftUpColor, leftDownColor, rightUpColor, rightDownColor, 0.5f);
		
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] {
				new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize),
				new Font("Credit valley", Font.BOLD, fontSize) });

		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});

		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef,
				postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

}
