import { NativeModules } from 'react-native';

type NativeJitsiMeetType = {
  call(url: string): Function
};

const { NativeJitsiMeet } = NativeModules;

export default NativeJitsiMeet as NativeJitsiMeetType;
