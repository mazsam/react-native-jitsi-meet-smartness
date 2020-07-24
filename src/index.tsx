import { NativeModules } from 'react-native';

type KoReactNativeJitsiMeetType = {
  multiply(a: number, b: number): Promise<number>;
};

const { KoReactNativeJitsiMeet } = NativeModules;

export default KoReactNativeJitsiMeet as KoReactNativeJitsiMeetType;
