import * as React from 'react';
import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import JitsiMeet from 'ko-react-native-jitsi-meet';

export default function App() {

  const call = React.useCallback(() => {
    JitsiMeet.call()
  }, [])
  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={call}>
        <Text>Start Jitsi</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
