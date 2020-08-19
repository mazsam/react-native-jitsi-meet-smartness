#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(JitsiMeet, NSObject)


RCT_EXTERN_METHOD(call:(NSString*)url userInfo:(NSDictionary *)userInfo)

@end
