import { ScrollView, StyleSheet, Dimensions } from "react-native";
import { useNavigation, useFocusEffect } from "@react-navigation/native";
import { useState, useEffect, useRef, useCallback } from "react";
import { getRecommendDiary } from "../../api/diary";

import RecommendDiary from "./RecommendDiary";
import RecommendGroup from "./RecommendGroup";
import RecentDiary from "./RecentDiary";
import BottomTabContainer from "../BottomTabContainer/BottomTabContainer";
import { getRecommendGroup } from "../../api/group";

const { width, height } = Dimensions.get("window");

const DIARYIES = [
  {
    id: 0,
    hashtags: "감정1",
    image: require("../../assets/images/SAMPLE6.png"),
  },
  {
    id: 1,
    hashtags: "감정2",
    image: require("../../assets/images/SAMPLE5.png"),
  },
  {
    id: 2,
    hashtags: "감정3",
    image: require("../../assets/images/SAMPLE4.png"),
  },
  {
    id: 3,
    hashtags: "감정4",
    image: require("../../assets/images/SAMPLE3.png"),
  },
  {
    id: 4,
    hashtags: "감정5",
    image: require("../../assets/images/SAMPLE1.png"),
  },
];
const Home = () => {
  const navigation = useNavigation();
  const initialDiaries = useRef([]);
  const initialGroups = useRef([]);

  const [diaries, setDiaries] = useState([]);
  const [groups, setGroups] = useState([]);

  const navigateToScreen = (screen, id) => {
    navigation.navigate(screen, { id: id });
  };

  useFocusEffect(
    useCallback(() => {
      const getRecoDiary = async () => {
        const res = await getRecommendDiary();
        if (JSON.stringify(res) !== JSON.stringify(initialDiaries.current)) {
          setDiaries(res);
        }
      };

      const getRecoGroup = async () => {
        const res = await getRecommendGroup();
        if (JSON.stringify(res) !== JSON.stringify(initialGroups.current)) {
          setGroups(res);
        }
      };

      getRecoDiary();
      getRecoGroup();

      return () => {};
    }, [])
  );

  useEffect(() => {
    console.log("a");
    initialDiaries.current = diaries;
    initialGroups.current = groups;
  }, [diaries, groups]);

  return (
    <BottomTabContainer>
      <ScrollView style={styles.container}>
        <RecommendGroup groups={groups} navigateToScreen={navigateToScreen} />
        <RecommendDiary diaries={diaries} navigateToScreen={navigateToScreen} />
        <RecentDiary diaries={DIARYIES} navigateToScreen={navigateToScreen} />
      </ScrollView>
    </BottomTabContainer>
  );
};

export default Home;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: width,
    height: height,
    paddingHorizontal: 16,
    margintTop: 8,
  },
});
