package org.linkerz.parser.helper;

/**
 * The Class Option.
 *
 * @author Nguyen Duc Dung
 * @since 12/31/12 11:57 PM
 */
public class Configuration {

    private boolean isShowTitle = false;
    private boolean isShowText = false;
    private boolean isShowDescription = false;
    private boolean isShowImage = false;

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public boolean isShowText() {
        return isShowText;
    }

    public void setShowText(boolean showText) {
        isShowText = showText;
    }

    public boolean isShowDescription() {
        return isShowDescription;
    }

    public void setShowDescription(boolean showDescription) {
        isShowDescription = showDescription;
    }

    public boolean isShowImage() {
        return isShowImage;
    }

    public void setShowImage(boolean showImage) {
        isShowImage = showImage;
    }
}
