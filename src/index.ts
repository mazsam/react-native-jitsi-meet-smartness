import { NativeModules, NativeEventEmitter } from 'react-native';
interface UserInfo {
  email: string;
  displayName: string;
}
interface FeatureFlags {
  [key: string]: boolean;
}
type JitsiMeetType = {
  call(url: string, userInfo: UserInfo, featureFlags: FeatureFlags): void;
};

const { JitsiMeet } = NativeModules;

export const eventEmitter = new NativeEventEmitter(JitsiMeet);

export default JitsiMeet as JitsiMeetType;
