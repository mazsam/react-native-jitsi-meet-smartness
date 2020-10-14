#import <React/RCTBridgeModule.h>

#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(JitsiMeet, RCTEventEmitter)


RCT_EXTERN_METHOD(join:(NSString*)url userInfo:(NSDictionary *)userInfo featureFlags:(NSDictionary *)featureFlags)

@end
