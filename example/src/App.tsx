import * as React from 'react';
import {
  StyleSheet,
  View,
  Text,
  Button,
  TextInput,
  ScrollView,
} from 'react-native';
import CheckBox from '@react-native-community/checkbox';
import JitsiMeet, { eventEmitter } from 'ko-react-native-jitsi-meet';

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
    <View style={{ flexDirection: 'row', alignItems: 'center' }}>
      <CheckBox
        disabled={disabled}
        value={value}
        onValueChange={onValueChange}
      />
      <Text style={{ color: 'ghostwhite' }}>{text}</Text>
    </View>
  );
};
type FeatureFlag = {
  [key: string]: boolean;
};
interface Props {
  flags: FeatureFlag;
}
const initialFlags = {
  'add-people.enabled': true,
  'chat.enabled': true,
  'close-captions.enabled': true,
  'controls-in-menu.enabled': false,
  'help.enabled': true,
  'invite.enabled': true,
  'kick-out.enabled': true,
  'meeting-name.enabled': true,
  'meeting-password.enabled': true,
  'pip.enabled': false,
  'remote-video-menu.enabled': true,
  'tile-view.enabled': false,
  'tile-default-view.enabled': false,
};
export default function App({ flags = initialFlags }: Props) {
  // Conference values
  const [url, setUrl] = React.useState('https://meet.jit.si/ko-saloon');
  const [email, setEmail] = React.useState('john@ko.com');
  const [events, setEvents] = React.useState<string[]>([]);
  const [displayName, setDisplayName] = React.useState('John Doe');

  // Features values
  const [featureFlags, setFeatureFlags] = React.useState<FeatureFlag>(flags);

  const call = React.useCallback(() => {
    JitsiMeet.call(url, { email, displayName }, featureFlags);
  }, [url, email, displayName, featureFlags]);

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener(
      'onConferenceTerminated',
      () => {
        setEvents((e) => [...e, 'onConferenceTerminated']);
      }
    );
    return eventListener.remove;
  }, []);

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener('onConferenceJoined', () => {
      setEvents((e) => [...e, 'onConferenceJoined']);
    });
    return eventListener.remove;
  }, []);

  return (
    <View style={styles.container}>
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

      <Button title="Join conference" color={buttonColor} onPress={call} />
      <ScrollView contentContainerStyle={{ flex: 1 }} style={{ flex: 1 }}>
        {events.map((event, index) => (
          <Text key={`text-${index}`}>{event}</Text>
        ))}
      </ScrollView>
    </View>
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
    marginVertical: 20,
  },
});
