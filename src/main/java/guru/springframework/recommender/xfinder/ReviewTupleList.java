package guru.springframework.recommender.xfinder; /*
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


 import guru.springframework.domain.Reviewer;

 import java.util.ArrayList;
import java.util.Date;
/*
@author: Sara Bahrami <mxbahramizanjani@wichita.edu>
*/

public class ReviewTupleList
{
    //MEMBER VARIABLES
    //ArrayList<Integer> mBugIDList;
    ArrayList<String> mBugIDList;
    ArrayList<Reviewer> mReviewerList;
    ArrayList<Date> mDateList;

    //CONSTRUCTORS
    //public ReviewTupleList(ArrayList<Integer> bugidList, ArrayList<String> reviewerList, ArrayList<Date> dateList)
    public ReviewTupleList(ArrayList<String> bugidList, ArrayList<Reviewer> reviewerList, ArrayList<Date> dateList)
    {
        mBugIDList = bugidList;
        mReviewerList=reviewerList;
        mDateList = dateList;
    }
}