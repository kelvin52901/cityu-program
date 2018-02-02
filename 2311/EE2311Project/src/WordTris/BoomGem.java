package WordTris;

import java.awt.Image;

import javax.swing.ImageIcon;

import WordTris.Resource.resourceSetting;

public class BoomGem extends Gem{	
	BoomGem(int x, int y) {
		super(null, 0, 0);
		Image boomimage = new ImageIcon(this.getClass().getResource(resourceSetting.GetBoomPath())).getImage();		
		super.setPic(boomimage);
		super.SetDisplayXY(x, y);
	}	
}
