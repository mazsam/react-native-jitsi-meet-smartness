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
export default function App() {
  // Conference values
  const [url, setUrl] = React.useState('https://meet.jit.si/ko-saloon');
  const [email, setEmail] = React.useState('john@ko.com');
  const [events, setEvents] = React.useState<string[]>([]);
  const [displayName, setDisplayName] = React.useState('John Doe');

  // Features values
  const [featurePipEnabled, setFeaturePipEnabled] = React.useState(false);

  const call = React.useCallback(() => {
    const featureFlags = {
      'pip.enabled': featurePipEnabled,
    };
    JitsiMeet.call(url, { email, displayName }, featureFlags);
  }, [url, email, displayName, featurePipEnabled]);

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
        <CheckBox
          disabled={false}
          value={featurePipEnabled}
          onValueChange={setFeaturePipEnabled}
        />
        <Text style={{ color: 'ghostwhite' }}>PIP_ENABLED</Text>
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
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 20,
  },
});
