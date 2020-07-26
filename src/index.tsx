import { NativeModules } from 'react-native';

type KoReactNativeJitsiMeetType = {
  multiply(a: number, b: number): Promise<number>;
};

const { NativeJitsiMeet } = NativeModules;

export default NativeJitsiMeet as KoReactNativeJitsiMeetType;
