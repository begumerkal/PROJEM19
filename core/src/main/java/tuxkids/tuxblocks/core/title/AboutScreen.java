package tuxkids.tuxblocks.core.title;

import java.util.ArrayList;
import java.util.List;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer.Event;
import playn.core.Pointer.Listener;
import playn.core.TextFormat;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;
import tuxkids.tuxblocks.core.Constant;
import tuxkids.tuxblocks.core.Lang;
import tuxkids.tuxblocks.core.screen.BaseScreen;
import tuxkids.tuxblocks.core.utils.CanvasUtils;
import tuxkids.tuxblocks.core.utils.PlayNObject;
import tuxkids.tuxblocks.core.widget.GameBackgroundSprite;

/**
 * Screens which shows information about the game.
 */
public class AboutScreen extends BaseScreen {

	// TODO: Clean this class up... it's functional but pretty shabby
	
	protected final GroupLayer scrollGroup;
	protected final float dy;
	
	protected float y;
	protected List<Float> dys = new ArrayList<Float>();
	
	public AboutScreen(ScreenStack screens, GameBackgroundSprite background) {
		super(screens, background);
		
		scrollGroup = graphics().createGroupLayer();
		layer.add(scrollGroup);
		
		ImageLayer clickCatcher = graphics().createImageLayer(
				CanvasUtils.createRect(1, 1, CanvasUtils.TRANSPARENT));
		clickCatcher.setSize(width(), height());
		clickCatcher.setDepth(10);
		layer.add(clickCatcher);
		
		// space between text sections
		dy = height() / 4;
		
		// intercept clicks anywhere and cause the text to advance
		clickCatcher.addListener(new Listener() {
			@Override
			public void onPointerStart(Event event) {
				if (dys.size() == 0) return; // not loaded yet
				if (dys.size() > 1) {
					// advance the text
					float d = dys.remove(0);
					y += d;
				} else {
					// finish
					popThis();
				}
			}
			
			@Override
			public void onPointerEnd(Event event) { }
			
			@Override
			public void onPointerDrag(Event event) { }
			
			@Override
			public void onPointerCancel(Event event) { }
		});
		
		Lang.getText(Constant.TEXT_ABOUT, new Callback<String>() {

			@Override
			public void onSuccess(String result) {
				String[] lines = result.split("\n");
				float border = height() / 30;
				float y = border;
				TextFormat format = PlayNObject.createFormat(height() / 15);
				format = format.withWrapWidth(width() - border * 2);
				for (String line : lines) {
					//use "\b" to make a newline, since \n indicates a split in the section 
					line = line.replace("\\b", "\n");
					Image image = CanvasUtils.createText(line, format, Colors.WHITE);
					ImageLayer imageLayer = graphics().createImageLayer(image);
					imageLayer.setTranslation(border, y);
					scrollGroup.add(imageLayer);
					float d = imageLayer.height() + dy;
					dys.add(d);
					y += d;
				}
			}

			@Override
			public void onFailure(Throwable cause) {
				cause.printStackTrace();
			}
			
		});
	}
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (!exiting()) {
			scrollGroup.setOrigin(0, PlayNObject.lerpTime(scrollGroup.originY(), y, 0.993f, clock.dt(), 1));
		}
	}

	@Override
	public void popThis() {
		popThis(screens.slide().up());
	}
}
