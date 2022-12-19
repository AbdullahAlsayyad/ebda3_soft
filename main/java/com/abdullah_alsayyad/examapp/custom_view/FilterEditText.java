package com.abdullah_alsayyad.examapp.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

/**
 * This view it's a regular "android.widget.EditText" by adding that you can prevent a character or a set of characters
 * and you can add animation "android.view.animation.Animation" when entering the forbidden character.
 *
 * You can add forbidden characters and animation by "setForbiddenCharacters" method.
 */
@SuppressLint("AppCompatCustomView")
public class FilterEditText extends EditText {
    private String forbiddenCharacters;
    private Animation animation = translateAnimation();
    private boolean doAnimation = true;

    public FilterEditText(Context context) {
        super(context);
    }
    public FilterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public FilterEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * For add forbidden characters.
     * @param forbiddenCharacters the forbidden characters.
     */
    public void setForbiddenCharacters(String forbiddenCharacters) {
        this.forbiddenCharacters = forbiddenCharacters;
        addFilter(null);
    }

    /**
     * For add forbidden characters and not allowing animation
     * @param forbiddenCharacters the forbidden characters.
     * @param doAnimation allowing animation .. it's true by default
     */
    public void setForbiddenCharacters(String forbiddenCharacters, boolean doAnimation) {
        this.doAnimation = doAnimation;
        setForbiddenCharacters(forbiddenCharacters);
    }

    /**
     * For add forbidden characters and animation
     * @param forbiddenCharacters the forbidden characters.
     * @param animation animation when entering the forbidden character... it's translate animation by default
     */
    public void setForbiddenCharacters(String forbiddenCharacters, Animation animation) {
        this.animation = animation;
        setForbiddenCharacters(forbiddenCharacters);
    }


    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        addFilter(watcher);
    }

    /**
     * To Filter text inserted
     * @param watcher if use ".addTextChangedListener"
     */
    private void addFilter(TextWatcher watcher){

        TextWatcher filterWatcher = new TextWatcher() {
//            first index of text inserted
            private int sIndex;
            
//            length of text inserted
            private int lengthOfCharactersAdded;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (watcher != null) watcher.beforeTextChanged(s, start, count, after);

                this.lengthOfCharactersAdded = after - count;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (watcher != null) watcher.onTextChanged(s, start, before, count);

                if (start == 0 && before == 0) this.sIndex = 0;
                else if (before == 0) this.sIndex = start;
                else this.sIndex = before;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (watcher != null) watcher.afterTextChanged(s);

                if (lengthOfCharactersAdded <=0) return;

                String textAfterFilter = deleteCharacters(new StringBuilder(s.toString()), sIndex, lengthOfCharactersAdded);

//                if "lengthDifference" equal 0 mean user not insert forbidden character
                int lengthDifference = (s.length()- textAfterFilter.length());

//                index for the pointer
                int selectionIndex = this.sIndex + lengthOfCharactersAdded - lengthDifference;

                FilterEditText.super.setText(textAfterFilter);
                FilterEditText.super.setSelection(selectionIndex);

                if (lengthDifference != 0 && doAnimation)
                    FilterEditText.super.startAnimation(animation);
            }
        };

        super.addTextChangedListener(filterWatcher);
    }

    private static AnimationSet translateAnimation(){
        TranslateAnimation ta1 = new TranslateAnimation(0, 10, 0, 0);
        ta1.setDuration(50);
        TranslateAnimation ta2 = new TranslateAnimation(10, 0, 0, 0);
        ta2.setStartOffset(50);
        ta2.setDuration(50);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(ta1);
        set.addAnimation(ta2);
        return set;
    }

    /**
     *
     * @param text All text
     * @param sIndex first index of text inserted
     * @param length length of text inserted
     * @return
     */
    private String deleteCharacters(@NotNull StringBuilder text, int sIndex, int length){
//        text inserted
        String subText = text.substring(sIndex, sIndex + length);

//        check of letters
        for (int i = 0; i < forbiddenCharacters.length(); i++)
            subText = subText.replace(forbiddenCharacters.charAt(i)+"", "");

        return text.replace(sIndex, sIndex+length, subText).toString();
    }


}
