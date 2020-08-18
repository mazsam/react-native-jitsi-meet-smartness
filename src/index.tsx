import { NativeModules } from 'react-native';

type NativeJitsiMeetType = {
  call(url: string): Function
};

const { JitsiMeet } = NativeModules;

export default JitsiMeet as NativeJitsiMeetType;
