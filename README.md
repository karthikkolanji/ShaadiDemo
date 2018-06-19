#This contains

**NOTE**

* Don't use Serializable . Use Parcelable (https://android.jlelse.eu/parcelable-vs-serializable-6a2556d51538)

* Use proper nomenclature for variable and views . Eg, Recyclerview (rv_),Imageview (iv_),Progressbar(pb_)





**BaseActivity**
This handles runtime permission. All subclass Activity has to only implement onPermissionGranted() methods, rest permission denied case is handled by this base class

**BaseFragment**
This handles runtime permission. All subclass Fragment has to only implement onPermissionGranted() methods, rest permission denied case is handled by this base class

**BaseView**
This interface has showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods that every Activity and Fragemenst needs as a call back on API call.

**RetrofitClient**
This handles creation of singleton Retrofit object. Implenting class can pass ServiceInterface class to access the API call method

**RetrofitCallbackWrapper**
This handles showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods globally , no need to every time implement this method while using Retrofit call. (https://medium.com/mindorks/rxjava2-and-retrofit2-error-handling-on-a-single-place-8daf720d42d6)

**SharedPrefUtil**
This handles creation of singleton SharedPreference object. Any class can access the getter() & setter() via KEY to get and set the values in SharedPrefernce file.

**ResourceFinder**
This contains methods to like getString(int resId),getColour(int resId),getDrawable(int resId),getDimension(int resId) readily available . Any class can get appropriate object by passing valid parameters without using any extra code.

