import * as React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Text,
  Button,
  TextInput,
  ScrollView,
} from 'react-native';
import CheckBox from '@react-native-community/checkbox';
import {
  join,
  Events,
  eventEmitter,
  FeatureFlag,
  FeatureFlags,
} from '@smartness-community/react-native-jitsi-meet';

const backgroundColor = 'cadetblue';
const buttonColor = '#f194ff';

interface FeatureTagCheckBoxProps {
  disabled?: boolean;
  value: boolean;
  onValueChange: (value: boolean) => void;
  text: string;
}

const FeatureTagCheckBox = ({
  disabled = false,
  value,
  onValueChange,
  text,
}: FeatureTagCheckBoxProps) => {
  return (
    <View style={{ flexDirection: 'row', alignItems: 'center', margin: 4 }}>
      <CheckBox
        disabled={disabled}
        value={value}
        onValueChange={onValueChange}
      />
      <Text style={{ color: 'ghostwhite', marginLeft: 4 }}>{text}</Text>
    </View>
  );
};
interface Props {
  flags: FeatureFlags;
}
const initialFlags = {
  [FeatureFlag.ADD_PEOPLE_ENABLED]: true,
  [FeatureFlag.CHAT_ENABLED]: true,
  [FeatureFlag.CLOSE_CAPTIONS_ENABLED]: true,
  [FeatureFlag.CONTROLS_IN_MENU_ENABLED]: false,
  [FeatureFlag.HANG_UP_WITH_BACK_BUTTON_ENABLED]: false,
  [FeatureFlag.HELP_ENABLED]: true,
  [FeatureFlag.INVITE_ENABLED]: true,
  [FeatureFlag.KICK_OUT_ENABLED]: true,
  [FeatureFlag.MEETING_NAME_ENABLED]: true,
  [FeatureFlag.MEETING_PASSWORD_ENABLED]: true,
  [FeatureFlag.PIP_ENABLED]: false,
  [FeatureFlag.REMOTE_VIDEO_MENU_ENABLED]: true,
  [FeatureFlag.TILE_VIEW_ENABLED]: false,
  [FeatureFlag.TILE_DEFAULT_VIEW_ENABLED]: false,
};
export default function App({ flags = initialFlags }: Props) {
  // Conference values
  const [url, setUrl] = React.useState('https://meet.jit.si/ko-saloon');
  const [email, setEmail] = React.useState('john@ko.com');
  const [events, setEvents] = React.useState<string[]>([]);
  const [displayName, setDisplayName] = React.useState('John Doe');

  // Features values
  const [featureFlags, setFeatureFlags] = React.useState<FeatureFlags>(flags);

  const joinConference = React.useCallback(() => {
    join(url, { email, displayName }, featureFlags);
  }, [url, email, displayName, featureFlags]);

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener(
      Events.onConferenceTerminated,
      () => {
        setEvents((e) => [
          ...e,
          `Conference terminated at ${new Date().toLocaleTimeString()}`,
        ]);
      }
    );
    return eventListener.remove;
  }, []);

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener(
      Events.onConferenceJoined,
      () => {
        setEvents((e) => [
          ...e,
          `Conference joined at ${new Date().toLocaleTimeString()}`,
        ]);
      }
    );
    return eventListener.remove;
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder={'Url'}
        defaultValue={url}
        onChangeText={setUrl}
      />
      <TextInput
        style={styles.input}
        placeholder={'Email'}
        defaultValue={email}
        onChangeText={setEmail}
      />
      <TextInput
        style={styles.input}
        placeholder={'Display Name'}
        defaultValue={displayName}
        onChangeText={setDisplayName}
      />
      <View style={styles.checkboxContainer}>
        {Object.keys(featureFlags).map((key: string) => {
          return (
            <FeatureTagCheckBox
              key={key}
              value={featureFlags[key]}
              onValueChange={(value) =>
                setFeatureFlags((prevState) => ({
                  ...prevState,
                  [key]: value,
                }))
              }
              text={key}
            />
          );
        })}
      </View>
      <Button
        title="Join conference"
        color={buttonColor}
        onPress={joinConference}
      />
      <ScrollView style={styles.terminal}>
        <Text style={styles.terminalLine}>
          jitsi{'>'} Join conference now !
        </Text>
        {events.map((event, index) => (
          <Text style={styles.terminalLine} key={`text-${index}`}>
            {`jitsi> ${event}`}
          </Text>
        ))}
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  button: {},
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-start',
    backgroundColor: backgroundColor,
  },
  input: {
    marginVertical: 2,
    backgroundColor: 'white',
  },
  checkboxContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    marginTop: 20,
    paddingTop: 20,
  },
  terminal: {
    backgroundColor: 'black',
    flex: 1,
    paddingHorizontal: 4,
    width: '100%',
  },
  terminalLine: {
    color: 'white',
    marginVertical: 5,
  },
});
