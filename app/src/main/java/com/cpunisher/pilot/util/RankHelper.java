package com.cpunisher.pilot.util;

import android.content.Context;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RankHelper {

    private static SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void recordScore(Context context, int score) {
        Date date = new Date();

        List<Rank> list = getSortedRankList(context);
        list.add(new Rank(ft.format(date), String.valueOf(score)));
        Collections.sort(list, new RankComparator());
        while (list.size() > 10) {
            list.remove(list.size() - 1);
        }
        writeRankList(context, list);
    }

    public static List<Rank> getSortedRankList(Context context) {
        try {
            File file = context.getFileStreamPath("rank");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = context.openFileInput("rank");
            List<Rank> list = new ArrayList<>();
            if (fis.available() != 0) {
                DataInputStream dis = new DataInputStream(fis);

                while (dis.available() != 0) {
                    list.add(new Rank(dis.readUTF(), dis.readUTF()));
                }
                dis.close();
                Collections.sort(list, new RankComparator());
            }

            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void writeRankList(Context context, List<Rank> list) {
        try {
            FileOutputStream fos = context.openFileOutput("rank", Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            for (Rank rank : list) {
                dos.writeUTF(rank.date);
                dos.writeUTF(rank.score);
            }
            dos.flush();
            dos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearRankList(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("rank", Context.MODE_PRIVATE);
            fos.write(0);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Rank implements Serializable {
        public String date;
        public String score;

        public Rank(String date, String score) {
            this.date = date;
            this.score = score;
        }
    }

    private static class RankComparator implements Comparator<Rank> {
        @Override
        public int compare(Rank o1, Rank o2) {
            return Integer.compare(Integer.valueOf(o2.score), Integer.valueOf(o1.score));
        }
    }
}
