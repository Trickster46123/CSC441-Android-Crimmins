# Lab 5 Notes

1. The first build took longer because I had to boot up the emulator and get everything running. My laptop also died during the classwork, so I had to restart and get everything set back up.

2. In dark mode, the background automatically changed from white to black, and the text changed from black to white.

3. The app UI is still new to me. I don't fully understand what all the different UI components and settings do yet.

4. 9/25: Week 5, Friday. I changed the Column padding from 24.dp to 40.dp. This moved the content farther away from the edges of the screen and made the surrounding space larger.
5. Week 6, Wednesday.

count is now 1 ,count is now 2, count is now 3

2. The count changed but the screen didn't because it was just a normal variable. The number was changing when I pressed the button but Compose was not watching it so the screen did not update.

3. remember keeps the value when the screen redraws. Without it the value would keep going back to what it started at.

Week 6, Friday.

1. If the minimum length rule came before isEmpty(), an empty goal would get the too short message instead of the empty message. This happens because the when statement stops at the first rule that is true.

2. I made my rule so a goal has to start with a letter. I chose this because it makes more sense for a goal to start with a letter instead of a random number or symbol.

| I typed                          | What the app did                                                            | Correct? |
|----------------------------------|-----------------------------------------------------------------------------|----------|
| Nothing                          | The Add goal button stayed disabled                                         | Yes      |
| Only spaces                      | The Add goal button stayed disabled                                         | Yes      |
| Hi                               | It said the goal was too short                                              | Yes      |
| workout                          | It said the goal was already on the list even with different capitalization | Yes      |
| 123                              | It said the goal needs to start with a letter                               | Yes      |
| Read                             | It added the goal to the list                                               | Yes      |
| Study Kotlin                     | It added the goal normally                                                  | Yes      |
| A goal longer than 40 characters | It stopped letting me type after 40 characters                              | Yes      |

## Week 7 — Wednesday (Lab 9)

1. After rotating, I was still on the All goals screen.

2. My two new goals were gone, and the list went back to the original four goals.

3. rememberSaveable kept track of which screen I was on after rotating. However, remember did not save the two goals I added, so the list reset to the original four.