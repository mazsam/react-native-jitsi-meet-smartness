import { NativeModules, NativeEventEmitter } from 'react-native';
export interface UserInfo {
  email: string;
  displayName: string;
}

export enum FeatureFlag {
  ADD_PEOPLE_ENABLED = 'add-people.enabled',
  CHAT_ENABLED = 'chat.enabled',
  CLOSE_CAPTIONS_ENABLED = 'close-captions.enabled',
  CONTROLS_IN_MENU_ENABLED = 'controls-in-menu.enabled',
  HANG_UP_WITH_BACK_BUTTON_ENABLED = 'hang-up-with-back-button.enabled',
  HELP_ENABLED = 'help.enabled',
  INVITE_ENABLED = 'invite.enabled',
  KICK_OUT_ENABLED = 'kick-out.enabled',
  MEETING_NAME_ENABLED = 'meeting-name.enabled',
  MEETING_PASSWORD_ENABLED = 'meeting-password.enabled',
  PIP_ENABLED = 'pip.enabled',
  REMOTE_VIDEO_MENU_ENABLED = 'remote-video-menu.enabled',
  TILE_VIEW_ENABLED = 'tile-view.enabled',
  TILE_DEFAULT_VIEW_ENABLED = 'tile-default-view.enabled',
}
export type FeatureFlags = {
  [key in FeatureFlag]: boolean;
};
type JitsiMeetType = {
  join(url: string, userInfo: UserInfo, features: FeatureFlags): void;
};

const { JitsiMeet } = NativeModules;

export const eventEmitter = new NativeEventEmitter(JitsiMeet);

export default JitsiMeet as JitsiMeetType;
