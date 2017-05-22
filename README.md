<h2>Introduction</h2>
<a href="https://en.wikipedia.org/wiki/Dynamic_programming" target="_blank">Dynamic programming</a> is a very powerful tool  for optimization that not all developers have encountered. 

So what is dynamic programming? Dynamic programming is a method for solving complex problems by dividing them into simpler subproblems and storing the solution to each subproblem so it can be looked up later. There is another type of algorithm, the "greedy" algorithm,  that also solves problem by dividing them into to subproblem but the difference is that for greedy algorithms you only use the result of the last subproblem when calculating the result for the following problem. The greedy approach will not work for more complicated problems.

<h2>Example: Using dynamic programming to attack the death star</h2>
Suppose you are the commander of the alliance fleet and you want to attack the death star. Rebel spies have gathered information about potential targets on the death star and calculated the amount of damage that would be caused by attacking these. The problem is how to deal with the protective shield surrounding the death star and the imperial star fleet. Now the alliance fleet has a brand new star ship that is able to penetrate the shield but this still leaves the problem with the imperial fleet, what to do? You come up with the idea that the rest of the rebel fleet should make a diversion by attacking an imperial stronghold on a nearby planet. This will create a window of opportunity for say 10 minutes. So the goal is to make as much possible damage to the death star given these 10 minutes.

You have the following available missions:
<table border="1px;width:400px;">
<tbody>
<tr>
<td>Mission nr</td>
<td>Estimated time (minutes)</td>
<td>Estimated damage on death star (%)</td>
</tr>
<tr>
<td>1</td>
<td>2</td>
<td>4</td>
</tr>
<tr>
<td>2</td>
<td>4</td>
<td>10</td>
</tr>
<tr>
<td>3</td>
<td>6</td>
<td>12</td>
</tr>
<tr>
<td>4</td>
<td>7</td>
<td>14</td>
</tr>
</tbody>
</table>
Let T be the total time (10 minutes).
So we have the following inputs:
<ul>
 	<li>Missions m<sub>1</sub>,....,m<sub>4</sub></li>
 	<li>Each mission takes time t<sub>1</sub>,...., t<sub>4</sub></li>
 	<li>Each mission causes damage d<sub>1</sub>,...., d<sub>4</sub></li>
        <li>Total time T = 10 minutes</li>
</ul>
What missions should we choose given the timeframe of 10 minutes to make the most damage?

<h2>Initial solution</h2>
To make things easier we will initially assume that the missions are "repeatable" that is it is possible to execute the same mission several times.

A naive and "greedy" approach would be to always choose the mission that has makes the most damage and is possible to execute given the time left. This would result in the following:
<table style="border: none; width: 300px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 210px;">7 m</td>
<td style="border: none; width: 60px;">2 </td>
<td style="border: none; width: 30px;">1 m</td>
</tr>
<tr>
<td style="border: 2px solid black; width: 210px; background: blue; color: white;">m<sub>4</sub></td>
<td style="border: 2px solid black; width: 60px; background: blue; color: white;">m<sub>1</sub></td>
<td style="border: 2px solid black; width: 30px; background: white; color: black;"></td>
</tr>
</tbody>
</table>
This results in a total of 18% damage. It is obvious that a better solution would be:
<table style="border: none; width: 300px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 120px;">4 m</td>
<td style="border: none; width: 120px;">4 m</td>
<td style="border: none; width: 60px;">2 m</td>
</tr>
<tr>
<td style="border: 2px solid black; width: 120px; background: blue; color: white;">m<sub>2</sub></td>
<td style="border: 2px solid black; width: 120px; background: blue; color: white;">m<sub>2</sub></td>
<td style="border: 2px solid black; width: 60px; background: blue; color: white;">m<sub>1</sub></td>
</tr>
</tbody>
</table>
This results in a total of 24% damage.

Now for this simple problem with few items (missions) and a few discrete values (amount of damage) it is pretty easy to find the optimal solution by visual inspection but when the number of items increases it will soon be impossible.

It is obvious that in order to find an optimal solution you must investigate different combinations of items (missions). One way of doing it would be to compare the value for all possible combinations of missions for a given time limit. Say that we have 10 items and that each item can be position in 1 of 20 locations that means 10^20 combinations = it will take more than a lifetime to calculate so we need a better solution. Dynamic programming to rescue!

We will try to find a solution to the problem by dividing it into subproblems.

Lets say we have an optimal solution for total time T and it contains missions m<sub>1</sub>, m<sub>i</sub>, m<sub>n</sub> with their corresponding times of t<sub>1</sub>, t<sub>i</sub>, t<sub>n</sub>:
<table style="border: none; width: 100px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 40px;">t<sub>1</sub></td>
<td style="border: none; width: 40px;">t<sub>i</sub></td>
<td style="border: none; width: 40px;">t<sub>n</sub></td>
</tr>
<tr>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">m<sub>1</sub></td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">m<sub>i</sub></td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">m<sub>n</sub></td>
</tr>
</tbody>
</table>
Now if we remove mission m<sub>i</sub> with time t<sub>i</sub>, clearly what is left is an optimal solution for time T - t<sub>i</sub>. This implies that if we want to calculate maximal damage for a given time, damage(t), then we can do this by solving damage(t - t<sub>i</sub>) + d<sub>i</sub>. Here "i" can denote  any mission with time t<sub>i</sub> and damage  d<sub>i</sub> but we dont know which one so we must try all of them.

Lets define the subproblems more formally:
<strong><em>D(t) is the maximal damage for that can be achieved given a timeframe of t.
Then D(t) can be defined using the subproblem: max { D(t - t<sub>i</sub>) + d<sub>i</sub>}. Where t<sub>i</sub> &lt;= t</em>
</strong>
So in order to find maximal D(t) the maximal damage for t - t<sub>i</sub> must already be known. We will therefore start with the simplest case e.g D(t<sub>0</sub>) = 0 and then move forward, minute by minute, until we reach t.

For a given timelimit t we will try to add all possible missions with time less or equal to the total available time and then choose the mission that result in greatest damage in total. To calculate the optimal total damage for a given time we need to know the optimal total damage for all cases with less time so that we can compare the net effect for adding a given mission.

An optimal vector for t<sub>0</sub>,...,t<sub>10</sub> will be:
<table style="border: none; width: 100px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 40px;">t<sub>0</sub></td>
<td style="border: none; width: 40px;">t<sub>1</sub></td>
<td style="border: none; width: 40px;">t<sub>2</sub></td>
<td style="border: none; width: 40px;">t<sub>3</sub></td>
<td style="border: none; width: 40px;">t<sub>4</sub></td>
<td style="border: none; width: 40px;">t<sub>5</sub></td>
<td style="border: none; width: 40px;">t<sub>6</sub></td>
<td style="border: none; width: 40px;">t<sub>7</sub></td>
<td style="border: none; width: 40px;">t<sub>8</sub></td>
<td style="border: none; width: 40px;">t<sub>9</sub></td>
<td style="border: none; width: 40px;">t<sub>10</sub></td>
</tr>
<tr>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0%</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">20%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">20%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">24%</td>
</tr>
</tbody>
</table>
How do we reach this result?
<ul>
 	<li>For t<sub>0</sub>, t<sub>1</sub> the maximal damage will be 0% since there are no missions that are less than 2 minutes</li>
 	<li>For t<sub>2</sub>, t<sub>3</sub> the maximal damage will be 4% since the only mission that can be executed is m<sub>1</sub></li>
 	<li>For t<sub>4</sub> things get a little more interesting. We can now choose to execute m<sub>1</sub> or m<sub>2</sub>. To calculate which one will result in most damage for the total solution we will compare these two options. To estimate the total damage for using m<sub>1</sub> we will look at the optimal damage for t<sub>4-2</sub> = 4% and then add the damage for m<sub>1</sub> (4%) which implies a total damage of 8%. To estimate the total damage for using m<sub>2</sub> we will look at the optimal damage for t<sub>4-4</sub> (0%) and then add the damage for m<sub>2</sub> (10%) which implies a total damage of 10%. To get maximal damage we therefore choose m<sub>2</sub>.</li>
</ul>
The following values are calculated in a similar way and note how we look up the results of previous subproblems two make the right decision for a given point in time.

The optimal damage given time = 10 minutes will be the value for t<sub>10</sub> which is 24% but what missions were involved for reaching this value?

To find the missions we need to backtrack which subproblems were used to arrive at the value of 24% for t<sub>10</sub>.
<ul>
 	<li>For t<sub>10</sub> we added mission m<sub>2</sub> that has time t<sub>2</sub> = 4 minutes</li>
 	<li>For t<sub>10 - 4</sub> we also added mission m<sub>2</sub> that has time t<sub>2</sub> = 4 minutes</li>
 	<li>For t<sub>10 - 4 - 4</sub> we added mission m<sub>1</sub>  that has time t<sub>1</sub> = 2 minutes</li></li>
</ul>
<table style="border: none; width: 100px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 40px;">t<sub>0</sub></td>
<td style="border: none; width: 40px;">t<sub>1</sub></td>
<td style="border: none; width: 40px;">t<sub>2</sub></td>
<td style="border: none; width: 40px;">t<sub>3</sub></td>
<td style="border: none; width: 40px;">t<sub>4</sub></td>
<td style="border: none; width: 40px;">t<sub>5</sub></td>
<td style="border: none; width: 40px;">t<sub>6</sub></td>
<td style="border: none; width: 40px;">t<sub>7</sub></td>
<td style="border: none; width: 40px;">t<sub>8</sub></td>
<td style="border: none; width: 40px;">t<sub>9</sub></td>
<td style="border: none; width: 40px;">t<sub>10</sub></td>
</tr>
<tr>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0%</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0%</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">4%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10%</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">14%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">20%</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">20%</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">24%</td>
</tr>
</tbody>
</table>



To summarize, the solution included:
<ul>
<li>
 m<sub>2</sub> (d<sub>2</sub> = 10%, t<sub>2</sub> = 4 minutes)
</li>
<li>
 m<sub>2</sub> (d<sub>2</sub> = 10%, t<sub>2</sub> = 4 minutes)
</li>
<li>
 m<sub>1</sub> (d<sub>1</sub> = 4%, t<sub>1</sub> = 2 minutes)
</li>
<li>
 Total damage 24%, total time = 10 minutes
</li>
</ul>
Below is function in Java that can be used to calculate a vector of damage for a given total time. The function returns a matrix where the first column contains the damage in procent and the second column is a pointer to the mission that was used chosen for the current time.
<code>
<pre>
int[][] optimalForRepeatableMissions(int totalTime, int[] missionTimes, int[] missionDamages) {
   int totalNrOfAvailableMissions = missionTimes.length;
   int damages[][] = new int[totalTime + 1][2]; // add one extra dimension for storing index for used mission

   for (int currentTotalTime = 1; currentTotalTime <= totalTime; currentTotalTime++) {
      damages[currentTotalTime][1] = -1;
      for (int missionNr = 1; missionNr <= totalNrOfAvailableMissions; missionNr++) {
         int missionTime = missionTimes[missionNr - 1];
         int damage = missionDamages[missionNr - 1];
         if (missionTime <= currentTotalTime) {<br/>
            int damageForAddingCurrentMission = damages[currentTotalTime - missionTime][0] + damage;
            if (damages[currentTotalTime][0] < damageForAddingCurrentMission) {
               damages[currentTotalTime][0] = damageForAddingCurrentMission;
               damages[currentTotalTime][1] = missionNr;
            }
         }
      }
   }
   return damages;
}

</pre>
</code>

To print the maximal damage for the previous example it can be invoked like this:
<code>
<pre>
int[] missionTimes = new int[] { 2, 4, 6, 7 };
int[] missionDamages = new int[] { 4, 10, 12, 14 };
int[][] damages = optimalForRepeatableMissions(10, missionTimes, missionDamages);
System.out.println(damages[damages.length - 1][0]);
</pre>
</code>

The expected running time for calculating maximal damage is <a href="https://www.khanacademy.org/computing/computer-science/algorithms/asymptotic-notation/a/big-o-notation" target="_blank">O(Tn)</a> where n is the number of available missions and T is total available time.

<h2>Final solution</h2>
Now consider a more "realistic" situation where the missions are not repeatable (its only possible to blow up a target once...) what changes must we do to our initial solution?

Previous we defined a subproblem as:
<strong><em>
max { D(t - t<sub>i</sub>) + d<sub>i</sub>}. Where t<sub>i</sub> &lt;= t
</em></strong>
But in the statement above we assumed that mission m<sub>i</sub> with  time t<sub>i</sub> and damage d<sub>i</sub> could be any mission. This is no longer true since a given mission can only be used once so we can only consider missions that have not been executed. We need to add an additional variable to indicate what missions are being used.

Therefore will use the following definitions:
<strong><em>D(t, i) is the maximal damage that can be achieved given a timeframe of t using missions 1,...,i
Then D(t, i) can be defined using the subproblem: max { D(t - t<sub>i</sub>, i - 1) + d<sub>i</sub>, D(t, i - 1)}
</em></strong>

<em>D(t - t<sub>i</sub>, i - 1)</em> will be applied when mission <em>i</em> is needed to achieve the optimal solution and <em>D(t, i - 1)</em> will be applied when <em>i</em> is not needed to achieve the optimal solution.

Instead of a vector the result will be a matrix with time on one axis and number of available missions on the other. We will calculate the maximum damage progressively looking up the damage for previous cases.

 
<table style="border: none; width: 100px; font-weight: bold;">
<tbody>
<tr>
<td style="border: none; width: 40px;"></td>
<td style="border: none; width: 40px;">t<sub>0</sub></td>
<td style="border: none; width: 40px;">t<sub>1</sub></td>
<td style="border: none; width: 40px;">t<sub>2</sub></td>
<td style="border: none; width: 40px;">t<sub>3</sub></td>
<td style="border: none; width: 40px;">t<sub>4</sub></td>
<td style="border: none; width: 40px;">t<sub>5</sub></td>
<td style="border: none; width: 40px;">t<sub>6</sub></td>
<td style="border: none; width: 40px;">t<sub>7</sub></td>
<td style="border: none; width: 40px;">t<sub>8</sub></td>
<td style="border: none; width: 40px;">t<sub>9</sub></td>
<td style="border: none; width: 40px;">t<sub>10</sub></td>
</tr>

<tr>
<td style="border: none; width: 40px;">m<sub>0</sub></td>
<td style="border: 2px solid black; width: 40px; background: green; color: white;">0</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">0</td>
</tr>

<tr>
<td style="border: none; width: 40px;">m<sub>1</sub></td>
<td style="border: 2px solid black; width: 40px; background: green; color: white;">0</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
</tr>

<tr>
<td style="border: none; width: 40px;">m<sub>2</sub></td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
</tr>

<tr>
<td style="border: none; width: 40px;">m<sub>3</sub></td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">16</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">16</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">22</td>
</tr>

<tr>
<td style="border: none; width: 40px;">m<sub>4</sub></td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 40px; background: blue; color: white;">0</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">4</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">10</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">14</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">16</td>
<td style="border: 2px solid black; width: 20px; background: blue; color: white;">18</td>
<td style="border: 2px solid black; width: 20px; background: green; color: white;">22</td>
</tr>
</tbody>
</table>

We find that using non repeatable missions leads to a total damage of 22% for time = 10 minutes. 

We can find the path taken through the matrix (ie what missions were used) by backtracking from D(t<sub>10</sub>, m<sub>4</sub>).
<ul>
<li>
We see that moving from  D(t<sub>10</sub>, m<sub>4</sub>) to  D(t<sub>10</sub>, m<sub>3</sub>) does not imply any change in damage, this means that m<sub>4</sub> was not included in the solution.
</li>
<li>
We see that moving from  D(t<sub>10</sub>, m<sub>3</sub>) to  D(t<sub>10</sub>, m<sub>2</sub>) implies a change in damage, this means that m<sub>3</sub> was included in the solution.
</li>
<li>
m<sub>3</sub> has a mission time of 6 minutes which means that we will move back 6 minutes to find previous solution so we end up on D(t<sub>4</sub>, m<sub>2</sub>)
</li>
<li>
Using the same reasoning again we can conlude that m<sub>2</sub> was included in the solution. 
</li>
<li>
m<sub>2</sub> has a mission time of 4 minutes which leads us back to time = 0 and we are finished
</li>
</ul>
To summarize, the final solution included:
<ul>
<li>
 m<sub>3</sub> (d<sub>3</sub> = 12%, t<sub>3</sub> = 6 minutes)
</li>
<li>
 m<sub>2</sub> (d<sub>2</sub> = 10%, t<sub>2</sub> = 4 minutes)
</li>
<li>
 Total damage 22%, total time = 10 minutes
</li>

</ul>


Below is the function in Java that is used to calculate the matrix above.
<code>
<pre>
int[][] optimalForNonRepeatableMissions(final int totalTime, final int[] missionTimes, final int[] missionDamages) {
   int totAvailableMissions = missionTimes.length;
   int damages[][] = new int[totalTime + 1][totAvailableMissions + 1];
   for (int missionNr = 1; missionNr <= totAvailableMissions; missionNr++) {
      int missionTime = missionTimes[missionNr - 1];
      int damage = missionDamages[missionNr - 1];
      for (int currentTotTime = 1; currentTotTime <= totalTime; currentTotTime++) {
         damages[currentTotTime][missionNr] = damages[currentTotTime][missionNr - 1];
         if (missionTime <= currentTotTime) {
            int damageAddingCurrentMission = damages[currentTotTime - missionTime][missionNr - 1] + damage;
            if (damages[currentTotTime][missionNr] < damageAddingCurrentMission) {
               damages[currentTotTime][missionNr] = damageAddingCurrentMission;
            }
         }
      }
   }
   return damages;
}
</pre>
</code>

The complete java code example can be downloaded from <a href="https://github.com/milin44/DynamicProgramming/blob/master/src/DeathStar.java">GitHub</a>

May the force be with you...


&nbsp;
