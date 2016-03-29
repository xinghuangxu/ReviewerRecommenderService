package xinghuangxu.codereview.recommender.xfinder; /*
  Copyright Software Engineering Research laboratory <serl@cs.wichita.edu>

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU Library General Public
 License as published by the Free Software Foundation; either
 version 2 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Library General Public License for more details.

 You should have received a copy of the GNU Library General Public
 License along with this program; if not, write to the Free
 Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 
 */


import xinghuangxu.codereview.domain.Reviewer;

/*
  * @author: Sara Bahrami <mxbahramizanjani@wichita.edu>
  * Date Created: 11/2013
  *
  * Purpose:
  * 	Allows the storage of authors/attachers and their xfactor together
  * so that they can be sorted based on xfactor.
  */
public class RecommendedReviewer implements Comparable<RecommendedReviewer> {
    //MEMBER VARIABLES
    private Reviewer reviewer;
    private Double score;

    public RecommendedReviewer(Reviewer reviewer, Double xfactor) {
        this.reviewer = reviewer;
        score = xfactor;
    }

    public String getName() {
        return reviewer.getName();
    }

    public String getEmail() {
        return reviewer.getEmail();
    }

    public String getUsername() {
        return reviewer.getUsername();
    }

    public Double getScore() {
        return score;
    }

    public void addXFactor(int value) {
        score += value;
    }

    //INHERITED METHODS
    public int compareTo(RecommendedReviewer arg) {
        //this is backward in order to use Collections.sort and get descending order
        if (this.score > arg.score)
            return -1;
        else if (this.score < arg.score)
            return 1;
        else
            return 0;
    }

}
