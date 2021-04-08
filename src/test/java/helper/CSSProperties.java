package helper;

import org.openqa.selenium.WebElement;

public class CSSProperties {
	public String getFontName(WebElement element) {
		String fontName = element.getCssValue("font-family");
		return fontName;
	}
}
