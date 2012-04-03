/*******************************************************************************
 * Copyright 2012 Alexandros Schillings
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package co.uk.alt236.restclient4android.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
 
public class MarqueeTextView extends TextView {
 
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
 
    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public MarqueeTextView(Context context) {
        super(context);
        init();
    }
    
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            setSelected(focused);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if(focused)
            super.onWindowFocusChanged(focused);
    }

    
    private void init(){
    	setFocusable(true);
    	setFocusableInTouchMode(true);
    	setSingleLine(true);
    	setEllipsize(TextUtils.TruncateAt.MARQUEE);
    	setMarqueeRepeatLimit(1);
    	
    	// This empty listener is actually needed.
    	setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			}
		});
    }
 
}
