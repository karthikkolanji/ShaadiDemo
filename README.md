# BaseCode
This conatins basic project structure with all necessary libraries implemented and singleton class configurations like Retrofit, SharedPreeference, ResourceFinder etc readily available. We can pull this repo and start continuing with new projects

This contains

**BaseActivity**
This handles runtime permission. All subclass has to only implement onPermissionGranted() methods, rest permission denied case is handled by this class

**BaseView**
This interface has showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods that very Activity and Fragemenst needs as a call back on API call.

**RetrofitClient**
This habdles creation of singleton Retrofit object. Implenting class can pass Service interface to access the API call method

**RetrofitCallbackWrapper**
This handles showLoading(),hideLoading(),showError(),hideError(),onSuccess() methods globally , no need to every time implement this method while using Retrofit call.
