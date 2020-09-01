import { NativeModules, NativeEventEmitter } from 'react-native';
interface UserInfo {
  email: string;
  displayName: string;
}

type JitsiMeetType = {
  call(url: string, userInfo: UserInfo): Function
};

const { JitsiMeet } = NativeModules;

export const eventEmitter = new NativeEventEmitter(JitsiMeet);

export default JitsiMeet as JitsiMeetType;
