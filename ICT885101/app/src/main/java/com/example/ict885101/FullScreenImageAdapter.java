package com.example.ict885101;

public class FullScreenImageAdapter extends PagerAdapter {

    private Activity _activity;
    private String[] mStrings;
    private LayoutInflater inflater;
    ImageLoader im;


    // constructor
    public FullScreenImageAdapter(Activity activity, String[] mStrings) {
        this._activity = activity;
        this.mStrings = mStrings;

        im = new ImageLoader(_activity);
    }

    @Override
    public int getCount() {
        return this.mStrings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        final Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image,
                container, false);
        RelativeLayout parentview = (RelativeLayout) viewLayout
                .findViewById(R.id.rel);
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        im.DisplayImage(mStrings[position], imgDisplay);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);
        /**
         * Setting the object for animation
         * */


        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}