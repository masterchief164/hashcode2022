#include <bits/stdc++.h>
using namespace std;
#define gc getchar_unlocked
#define fo(i, n) for (i = 0; i < n; i++)
#define Fo(i, k, n) for (i = k; k < n ? i < n : i > n; k < n ? i += 1 : i -= 1)
#define ll long long
#define si(x) scanf("%d", &x)
#define sl(x) scanf("%lld", &x)
#define ss(s) scanf("%s", s)
#define pi(x) printf("%d\n", x)
#define pl(x) printf("%lld\n", x)
#define ps(s) printf("%s\n", s)
#define deb(x) cout << #x << "=" << x << endl
#define deb2(x, y) cout << #x << "=" << x << "," << #y << "=" << y << endl
#define pb push_back
#define mp make_pair
#define F first
#define S second
#define all(x) x.begin(), x.end()
#define clr(x) memset(x, 0, sizeof(x))
#define sortall(x) sort(all(x))
#define tr(it, a) for (auto it = a.begin(); it != a.end(); it++)
#define PI 3.1415926535897932384626
#define endl "\n"
#define YES cout << "YES" \
                 << "\n";
#define NO cout << "NO" \
                << "\n";
typedef pair<ll, ll> pii;
typedef pair<ll, ll> pl;
typedef vector<ll> vi;
typedef vector<ll> vl;
typedef vector<pii> vpii;
typedef vector<pl> vpl;
typedef vector<vi> vvi;
typedef vector<vl> vvl;
mt19937_64 rang(chrono::high_resolution_clock::now().time_since_epoch().count());

map<string, ll> like, dislike;
set<string> ing;
vector<string> ings;
vector<pair<ll, string>> ele;
set<string> ans;

void readfile(string name) // reads all the raw data and converts it into the objects
{
    ll i, n, m, j;
    string s;
    ifstream filena(name); // opens the file

    ll t = 0;
    filena >> t;
    fo(i, t)
    {
        filena >> n;
        fo(j, n)
        {
            filena >> s;
                }
        filena >> m;
        fo(j, m)
        {
            filena >> s;
        }
    }
    filena.close(); // file read complete
}

void solve()
{
    ll i, n, m, j;
    
}

void printData()
{
    ll i;
    cout << ans.size() << " ";
    for (auto x : ans)
    {
        cout << x << " ";
    }
    cout << "\n";
}

void write_data(string name)
{
    vl intersectionmap;
    ofstream fileout;
    fileout.open(name);
    ll i = 0;
    fileout << ans.size() << " ";
    for (auto x : ans)
    {
        fileout << x << " ";
    }
    fileout.close();
}

int main()
{
    string input[] = {"a_an_example.in", "b_basic.in", "c_coarse.in", "d_difficult.in", "e_elaborate.in"};
    string output[] = {"a_an_example.out", "b_basic.out", "c_coarse.out", "d_difficult.out", "e_elaborate.out"};
    ll i;
    fo(i, 5)
    {
        like.clear();
        dislike.clear();
        ing.clear();
        ings.clear();
        ele.clear();
        ans.clear();
        readfile(input[i] + ".txt");
        solve();
        printData();
        write_data(output[i] + ".txt");
    }
    return 0;
}