import { NativeModules } from 'react-native';
interface UserInfo {
  email: string;
  displayName: string;
}

type JitsiMeetType = {
  call(url: string, userInfo: UserInfo): Function
};

const { JitsiMeet } = NativeModules;

export default JitsiMeet as JitsiMeetType;
