import { View, StyleSheet } from "react-native";

import Button from "../../ui/Button";

const Buttons = () => {
  return (
    <View style={styles.buttons}>
      <Button>전체</Button>
      <Button>일기</Button>
      <Button>소모임 일기</Button>
    </View>
  );
};

export default Buttons;

const styles = StyleSheet.create({
  buttons: {
    flexDirection: "row",
    justifyContent: "flex-start",
    marginBottom: 34,
  },
});
