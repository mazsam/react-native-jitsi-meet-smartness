import { NativeModules } from 'react-native';

type NativeJitsiMeetType = {
  multiply(a: number, b: number): Promise<number>;
  call(): Function
};

const { NativeJitsiMeet } = NativeModules;

export default NativeJitsiMeet as NativeJitsiMeetType;
