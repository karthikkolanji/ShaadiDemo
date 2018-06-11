# BaseCode
This conatins basic project structure with all necessary libraries implemented and singleton class configurations like Retrofit, SharedPreeference, ResourceFinder etc readily available. We can pull this repo and start continuing with new projects

This contains

**BaseActivity**
This handles runtime permission. All subclass Activity has to only implement onPermissionGranted() methods, rest permission denied case is handled by this base class

**BaseFragment**
This handles runtime permission. All subclass Fragment has to only implement onPermissionGranted() methods, rest permission denied case is handled by this base class

**BaseView**
This interface has showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods that very Activity and Fragemenst needs as a call back on API call.

**RetrofitClient**
This handles creation of singleton Retrofit object. Implenting class can pass Service interface to access the API call method

**RetrofitCallbackWrapper**
This handles showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods globally , no need to every time implement this method while using Retrofit call.

**SharedPrefUtil**
This handles creation of singleton SharedPreference object. Any class can access the getter() & setter() via KEY to get and set the values in SharedPrefernce file.

**ResourceFinder**
This contains methods to like getString(int resId),getColour(int resId),getDrawable(int resId),getDimension(int resId) readily available . Any class can get appropriate object by passing valid parameters without using any extra code.
