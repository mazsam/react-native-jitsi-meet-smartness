import * as React from 'react';
import { StyleSheet, View, Text, TouchableOpacity, TextInput, ScrollView } from 'react-native';
import JitsiMeet, { eventEmitter } from 'ko-react-native-jitsi-meet';

export default function App() {
  const [url, setUrl] = React.useState('https://meet.jit.si/exemple')
  const [email, setEmail] = React.useState('john@ko.com')
  const [events, setEvents] = React.useState<string[]>([])
  const [displayName, setDisplayName] = React.useState('John Doe')
  const call = React.useCallback(() => {
    JitsiMeet.call(url, { email, displayName })
  }, [])

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener('onConferenceTerminated', () => {
      setEvents(events => [...events, 'onConferenceTerminated'])
    })
    return () => eventListener.remove();
  }, [])

  React.useEffect(() => {
    const eventListener = eventEmitter.addListener('onConferenceJoined', () => {
      setEvents(events => [...events, 'onConferenceJoined'])
    })
    return () => eventListener.remove();
  }, [])

  return (
    <View style={styles.container}>
      <TextInput style={styles.input} placeholder={"Url"} defaultValue={url} onChangeText={setUrl} />
      <TextInput style={styles.input} placeholder={"Email"} defaultValue={email} onChangeText={setEmail} />
      <TextInput style={styles.input} placeholder={"Display Name"} defaultValue={displayName} onChangeText={setDisplayName} />
      <TouchableOpacity onPress={call} style={{ padding: 20, backgroundColor: 'red' }}>
        <Text>Start Jitsi</Text>
      </TouchableOpacity>
      <ScrollView contentContainerStyle={{ flex: 1 }} style={{ flex: 1 }} >
        {events.map((event, index) => <Text key={`text-${index}`}>{event}</Text>)}
      </ScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  input: {
    marginVertical: 4
  }
});
